import os
import sys
from hashlib import md5
ans = {}
def myinput(wmsg="", val=None):
    what = input()
    while (val != None and what not in val) or val == None:
        print(wmsg)
        what = input()
    return what
def chicken(sizead):
    new = {}
    best = {}
    for si, ads in sizead.items():
        if len(ads) > 1:
            size = {}
            for ad in ads:
                with open(ad, "rb") as file_:
                    hash_ = md5(file_.read()).hexdigest()
                    pre_value_h = size.get(hash_, [])
                    pre_value_h.append(ad)
                    size[hash_] = pre_value_h
                new[si] = size
    for i, j in new.items():
        m = {}
        for p, q in j.items():
            if len(q) > 1:
                m[p] = q
        if len(m) > 0:
            best[i] = m
    new.clear()
    return best
args = sys.argv
if len(args) < 2:
    print("Directory is not specified")
else:
    print("Enter file format:")
    form = input()
    print("Size sorting options:\n1. Descending\n2. Ascending")
    order = myinput("Wrong option", ["1", "2"])
    order = -1 if order == "1" else 1
    for root, folder, file in os.walk(args[1]):
        for name in file:
            if form not in name[-len(form):]:
                continue
            path = os.path.join(root, name)
            size = os.path.getsize(path)
            pre_value = ans.get(size, [])
            pre_value.append(path)
            ans.update({size: pre_value})
    for i, j in sorted(ans.items())[::order]:
        print(i, "bytes")
        print(*j, sep="\n", end="\n\n")
    print("Check for duplicates?")
    check = myinput("Wrong option", ["yes", "no"])
    if check == "yes":
        global duplicates
        hashes = chicken(ans)
        noo = 1
        duplicates = {}
        for i, j in  sorted(hashes.items())[::order]:
            if j:
                print(i, "bytes")
                for p, q in j.items():
                    print("Hash: " + p)
                    for r in q:
                        print(str(noo) + '. ' + r)
                        duplicates[str(noo)] = [r, i]
                        noo += 1
    print("Delete files?")
    delete_ = myinput("Wrong option", ["yes", "no"])
    if delete_ == "yes":
        while True:
            print("Enter file numbers to delete:")
            try:
                nums = input().split()
                if len(nums) == 0:
                    raise Exception
                need = [duplicates[i] for i in nums]
                total = 0
                for i in need:
                    os.remove(i[0])
                    total += i[1]
                print(f"Total freed up space: {total} bytes")
                break
            except Exception:
                print("Wrong format")
