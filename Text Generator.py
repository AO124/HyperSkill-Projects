from nltk.tokenize import regexp_tokenize
import random
import re

link = input()
with open(link, "r", encoding="utf-8") as corpus_file:
    corpus = regexp_tokenize(corpus_file.read(), "\S+")
    num = len(corpus)
    morcov = {}
    for i, k, j in zip(corpus[:num - 1], corpus[1:num], corpus[2:]):
        head = morcov.setdefault((i, k), {})
        tail = head.setdefault(j, 0) + 1
        morcov[(i, k)].update({j:tail})
    for _ in range(10):
        sen = []
        sen.extend(random.choice(list(filter(lambda x: re.match("[A-Z][^!?.]*$", x[0]), morcov.keys()))))
        while True:
            sen.append(random.choices(population=list(morcov[(sen[-2], sen[-1])].keys()), weights=list(morcov[(sen[-2], sen[-1])].values()),k = 1)[0])
            if sen[-1][-1] in ["?", ".", "!"] and len(sen) >= 5:
                break
        print(*sen)
