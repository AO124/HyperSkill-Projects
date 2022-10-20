visit = 0
inval = 0
running = 0
msg = ["Enter your board dimensions: ", "Enter the knight's starting position: ", "Enter your next move: "]
def s_input(str="", m=0):
    x, y = None, None
    while True:
        try:
            str = ("Invalid move! " + str) if inval > 0 else str
            c, r = input(str).split()
            x = int(r) - m
            y = int(c) - m
            if x < 0 or y < 0 or (str == msg[0] and (x == 0 or y== 0)):
                raise Exception
            else:
                break
        except Exception:
            print("'Invalid dimensions!'")
    return [x, y]

def printer(b=None):
    if b is None:
        b = board
    l3 = len(str(r))
    len1 = (n_ + 1)* c + 3
    print(" " * l3 + "-" * len1)
    for i in range(r, 0, -1):
        l4 = l3 - len(str(i))
        print(' ' * l4+str(i) + '|', " ".join(b[i - 1]),'|')
    print(" " * l3 + "-" * len1)
    print(" " * l3 + " ",  end=" ")
    for n in range(1,c + 1):
        l4 = n_ - len(str(n))
        print(" " * l4 + str(n), end=" ")
    print()

def move(x, y, need=False):
    hint = 0
    m = {2:1, 1:2}
    m_board = [Haha[:] for Haha in board]
    for n in m:
        for p in [1,-1]:
            for q in [1, -1]:
                i = (n * p ) + x
                j = (m[n] * q ) + y
                try:
                    if i < 0 or j < 0:
                        continue
                    if m_board[i][j] == cell[3]:
                        if need:
                            hint += 1
                        else:
                            m_board[i][j] = cell[1]
                except Exception:
                    print("", end="")
    if need:
        return str(hint)
    else:
        return m_board

def hinter(m_board):
    for j in range(len(m_board)):
        for i in range(len(m_board[j])):
            if m_board[j][i] == cell[1]:
                m_board[j][i] = cell[0] + move(j, i, True)
    return m_board

def game():
    global visit, inval, board, temp, pre, holy
    while True:
        if visit == 0:
            try:
                x, y = s_input(msg[1], 1)
                board[x][y] = cell[2]
                pre = [x, y]
                temp = hinter(move(x, y))
                holy = temp
                visit += 1
                break
            except Exception:
                print("Invalid position!")
        else:
            x, y = s_input(msg[2], 1)
            try:
                if temp[x][y].replace(cell[0], "").isnumeric():
                    board[x][y] = cell[2]
                    op, om = pre
                    board[op][om] = cell[0] + "*"
                    pre = [x, y]
                    temp = hinter(move(x, y))
                    inval = 0
                    visit += 1
                    break
                else:
                    raise Exception
            except Exception:
                inval += 1
    if visit > 1:
        printer(temp)
def creat_board():
    global r, c, board, n_, cell, visit, inval
    r, c = s_input(msg[0])
    n_ = len(str(r * c))
    cel = " " * (n_ - 1)
    cell = [cel, cel + "O", cel + "X", "_" * n_]
    board = [["_" * n_ for j in range(c)] for i in range(r)]
creat_board()

def run():
    if visit == r * c:
        print("What a great tour! Congratulations!")
        return False
    elif visit > 0:
        for i in temp:
            for j in i:
                if j.replace(cell[0], "").isnumeric():
                    return True
    elif visit < 1:
        return True
    print(f"No more possible moves!\nYour knight visited {visit} squares!")
    return False

def soler(soli):
    for i in soli:
        ind = str(soli.index(i) + 1)
        board[i[0]][i[1]] = " " * (n_ - len(ind)) + ind
    printer(board)

def max_(lis):
    if len(lis) == 0:
        return []
    a = lis[0]
    for each in lis:
        if len(each) > len(a):
            a = each
    return a

ans = None
def love(sol):
    global ans
    if not ans:
        if len(sol) == (r * c):
            ans = sol
            return sol
        this = []
        x = sol[-1][0]
        y = sol[-1][1]
        m = {2:1, 1:2}
        for n in m:
            for p in [1,-1]:
                for q in [1, -1]:
                    i = (n * p ) + x
                    j = (m[n] * q ) + y
                    if i > -1 and j > -1 and i < r and j < c and ([i, j] not in sol):
                        this.append([i, j])
        for ok in this:
            this[this.index(ok)] = love(sol + [ok])
        return max_(this)
    else:
        return ans

def solution():
    exist = True
    cani = love([pre])

    return cani
while run():
    game()
    if visit == 1:
        try_ = None
        while True:
            try_ = input("Do you want to try the puzzle? (y/n): ")
            if try_ in ["y", "n"]:
                break
            else:
                print("Invalid input!")
        so = solution()
        if len(so) == 0:
            print("No solution exists!")
            break
        else:
            if try_ == "n":
                print("Here's the solution!")
                soler(so)
                break
