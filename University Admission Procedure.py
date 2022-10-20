link = "applicants.txt"    #physics 2, chemistry 3, math 4, computer science 5
subs = {"Biotech": [[], [2, 3]], "Chemistry": [[], [3, 3]], "Engineering": [[], [4, 5]], "Mathematics": [[], [4, 4]], "Physics": [[], [2, 4]]}
def mean(s, st):
    m = -round((float(st[subs[s][1][0]]) + float(st[subs[s][1][1]])) / 2, 1)
    a = -round(float(st[6]))
    return a if m > a else m
num = int(input())
with open(link, "r") as every:
    lst = [i.split() for i in every.read().split("\n")]
    for n in range(7, 10):
        for sub in subs:
            lst2 = sorted(list(filter(lambda x: x[n] == sub, lst)), key=lambda x: (mean(sub, x), x[0], x[1]))
            for std in lst2:
                if len(subs[sub][0]) < num:
                    subs[sub][0].append(std[:2] + [mean(sub, std)])
                    lst.remove(std)
    for k, v in subs.items():
        with open(k.lower() + ".txt",  "w") as approved:
             for x in sorted(v[0], key=lambda x: (x[2], x[0], x[1])):
                 print(*x[:2], -x[2], file=approved)
