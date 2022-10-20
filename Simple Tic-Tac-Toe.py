a = "_________"
game = [[a[i] for i in range(3)], 
[a[i] for i in range(3, 6)], 
[a[i] for i in range(6, 9)]]
def printer():
    print("---------")
    for i in game:
        print("| " + " ".join(i) + " |")
    print("---------")
def move(r, c, w):
    global game, turn
    game[r - 1][c - 1] = who
    printer()
    resulto = result()
    if resulto == "continue":
        turn += 1
        return False
    else:
        print(resulto)
    return True
def result():
    win = ""
    x_num = 0
    o_num = 0
    _num = 0
    for r in range(0, 3):
        for c in range(0, 3):
            i = game[r][c]
            if i == "X":
                x_num += 1
            elif i == "O":
                o_num += 1
            else:
                _num += 1
    for i in "XO":
        for r in range(3):
            if game[r][0] == game[r][1] == game[r][2] == i:
                win += i
            if game[0][r] == game[1][r] == game[2][r] == i:
                win += i
            if game[0][0] == game[1][1] == game[2][2] == i:
                win += i
            if game[0][2] == game[1][1] == game[2][0] == i:
                win += i
    if len(win) == 0:
        if _num != 0:
            return "continue"
        else:
            return "Draw"
    else:
        return f"{win} wins"    
printer()
turn = 0
while True:
    who = "X" if turn % 2 == 0 else "O"
    cord = input()
    if cord.replace(" ","").isnumeric() and cord[1] == " " and len(cord) == 3:
        (r, c) = cord.split()
        r = int(r)
        c = int(c)
        if 0 < r < 4 and 0 < c < 4:
            if game[r - 1][c - 1] == "_":
                if move(r, c, who):
                    break
            else:
                print("This cell is occupied! Choose another one!")
        else:
            print("Coordinates should be from 1 to 3!")
    else:
        print("You should enter numbers!")    
        
