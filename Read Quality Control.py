import gzip
ans = []
def printer(l):
    print(f"Reads in the file = {l['reads']}:")
    print(f"Reads sequence average length = {l['avlen']}")
    print(f"Repeats = {l['rep']}")
    print(f'Reads with Ns = {l["ns"]}')
    print(f"GC content average = {l['gc']}%")
    print(f"Ns per read sequence = {l['perns']}%")
def cal(flink):
    link = gzip.open(flink, "rt")
    m = link.readlines()
    a = [str(i[:-1]) for i in m]
    n = a[1::2]
    read = n[::2]

    times = dict()
    gc = 0
    rep = 0
    an = []
    ize = 0

    for i in read:
        times[len(i)] = times.get(len(i), 0) + 1
        g = i.count("G")
        a = i.count("A")
        c = i.count("C")
        t = i.count("T")
        noo = i.count('N') / len(i) * 100
        gc += (g + c) / len(i) * 100
        if noo > 0:
            an.append(noo)
    s = 0
    t = 0
    for k, v in times.items():
        s += k * v
        t += v
    avgc = round(gc / t, 2)
    s = round(s / t)

    link.close()

    ns = 0
    for my in an:
        ns += my

    ns = round(ns / len(read), 2)

    ans.append({"reads": len(read), "avlen": s, "rep": len(read) - len(set(read)), "ns": len(an), "gc": avgc, "perns": ns} )
cal(input())
cal(input())
cal(input())
def maxer():
    r = 0
    n = 0
    ns = 0
    for i in range(1, len(ans)):
        r = i if   ans[i]["rep"] < ans[r]["rep"] else r
        n = i if   ans[i]["perns"] < ans[n]["perns"] else n
        ns = i if   ans[i]["ns"] < ans[ns]["ns"] else ns
    global max
    max = n
maxer()
printer(ans[0])
