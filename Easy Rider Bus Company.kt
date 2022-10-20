import json
import re
def stops(js):
    buses = json.loads(js)
    st = {}
    stl = {"S": (set(), 10, "Start stops:"), "": (set(), 0, "Transfer stops:"), "F": (set(), -10, "Finish stops:"), "O": (set(), 0, "Hi")}
    transfer = {}
    for bus in buses:
        id_ = bus["bus_id"]
        s = bus["stop_type"]
        p = bus["stop_name"]
        transfer[p] = transfer.get(p, 0) + 1
        if s in ["S", "F", "O"]:
            st[id_] = st.get(id_, 0) + stl[s][1]
            stl[s][0].add(p)
    for x, y in st.items():
        if y != 0:
            print(f"There is no start or end stop for the line: {x}.")
            return
    for x, y in transfer.items():
        if y > 1:
            stl[""][0].add(x)
    ans = []
    rest = stl[""][0].union(stl["S"][0].union(stl["S"][0]))
    for i in stl["O"][0]:
        if i in rest:
            ans.append(i)
    print("On demand stops test:")
    if ans:
        print("Wrong stop type:", sorted(ans))
    else:
        print("OK")
        
stops(input())
