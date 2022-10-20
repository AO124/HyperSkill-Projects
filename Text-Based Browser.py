import sys
import os
import requests
from bs4 import BeautifulSoup
from colorama import Fore
direct = sys.argv[1]
exist = os.access(direct, os.F_OK)
if not exist:
    os.mkdir(direct)

stack = []
prev = None
# write your code here
while True:
    stack.append(prev)
    link = input()
    dot = link.find(".")
    path = os.path.join(direct, link[:dot] if dot != -1 else link)
    if link == "back":
        x = stack[:-1].pop()
        print(x)
    elif link == "exit":
        break
    elif dot != -1:
        link = "http://" + link if "http://" not in link else link
        response = requests.get(link)
        if response:
            soup = BeautifulSoup(response.content, "html.parser")
            for i in soup.find_all("a"):
                i.string = "".join([Fore.BLUE, i.get_text(), Fore.RESET])
            content = soup.get_text()
            print(content)
            with open(path, "w", encoding="utf-8") as a:
                print(content, file=a)
                prev = content
    elif dot == -1 and os.access(path, os.F_OK):
        with open(path, "r", encoding="utf-8") as a:
            print(a.read())
    else:
        print("Incorrect URL")
