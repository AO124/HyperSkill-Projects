single = lambda inp: inp[0] in ["|", "."] or inp[0] == inp[-1]
def simple(string):
    re, st = string.split('|')
    if len(re) == 0:
        return True
    start = re[0] == "^"
    end = re[-1] == "$"
    re = re.replace("^", "").replace("$", "").replace("\?", "[").replace("\.", "]").replace("\*", "(").replace("\+", ")")
    st = st.replace("?", "[").replace(".", "]").replace("*", "(").replace("+", ")")
    if string + ">" == r"\\|\>":
        return True
    if len(st) == 0:
        return False
    t = -1 if (end == start == False and st[0] != re[0]) or end else 1
    re, st = (re[::t], st[::t])
    while len(re) != 0:
        if len(st) == 0:
            return False
        x = re[0]
        y = st[0]
        if len(re) < 2 or re[1 if t == 1 else 0] not in "?*+":
            if single(x + "|" + y):
                re = re[1:]
                st = st[1:]
            else:
                return False
        elif len(re) > 1 and re[1 if t == 1 else 0] in "+*?":
            mn = re[1 if t == 1 else 0]
            if mn == "?" and single(re[0 if t == 1 else 1] + "|" + y):
                st = st[1:]
            elif mn in "*+":
                tim = 0
                while len(st) > 0:
                    
                    yy = st[0]
                    if single(re[0 if t == 1 else 1] + "|" + yy):
                        if len(re) > 2 and len(st) > 0 and re[2] == st[0]:
                            break
                        tim += 1
                        if len(st) > 0:
                            st = st[1:]
                        else:
                            break
                    else:
                        break
                if tim == 0 and mn == "+":
                    return False            
            re = re[2:]
    if end and start and len(st) != 0:
        return False
    return len(re) == 0
    
print(simple(input()))
