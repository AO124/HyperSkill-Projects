import requests
import string
from bs4 import BeautifulSoup
import os
num = int(input())
atype = input()
pages = "https://www.nature.com/nature/articles?searchType=journalSearch&sort=PubDate&year=2020&page={n}"
for page in range(1, num + 1):
    folder = f"page_{page}"
    if os.access(folder, os.F_OK):
        for i, j, k in os.walk(folder):
            for p in k:
                os.remove(os.path.join(i,p))
            for q in j:
                os.rmdir(os.path.join(i, q))
        os.rmdir(folder)
    os.mkdir(folder)
    direct = os.getcwd()
    link = pages.format(n=page)
    response = requests.get(link, headers={'Accept-Language': 'en-US,en;q=0.5'})
    if response:
        soup = BeautifulSoup(response.content, "html.parser")
        articles = filter(lambda x: atype == x.find("span", "c-meta__type").text, soup.find_all("article"))
        article = map(lambda x: "https://www.nature.com" + str(x.find("a", {"data-track-action": "view article"}).get("href")), articles)
        for i in article:
            iresponse = requests.get(i, headers={'Accept-language': 'en-US,en; q=0.5'})
            if iresponse:
                isoup = BeautifulSoup(iresponse.content, "html.parser")
                title = "".join(list(filter(lambda x: x not in string.punctuation, str(isoup.title)[7:-8]))).replace(' ', '_') + ".txt"

                with open(os.path.join(direct, folder, title), "wb") as ifile:
                    out = isoup.find("div", {'class': "c-article-body"})
                    if out:
                        ifile.write((out.text).encode('utf-8'))
    else:
        print(f"The URL returned {response.status_code}!")
print("Saved all articles.")
