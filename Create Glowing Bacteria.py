def attach(plas, gf):
    return plas.replace(" ", gf)
add = input()
with open(add, 'r') as ex:
    p, g = ex.read().split("\n")
    q, r = p[:len(p) // 2], p[len(p) // 2 + 1:]
    h, i = g.split()

    print(attach(q, h))
    print(attach(r, i))
