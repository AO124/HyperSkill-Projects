class Teris:
    index = lambda self, ok, lr, ud:[(i // 10 + ud, i % 10 + lr) for i in ok] # plz don't go wrong'
    show = lambda self, b=None: [print(" ".join(i)) for i in (b if b else self.board)]
    O = [[4, 14, 15, 5]]
    I = [[4, 14, 24, 34], [3, 4, 5, 6]]
    S = [[5, 4, 14, 13], [4, 14, 15, 25]]
    Z = [[4, 5, 15, 16], [5, 15, 14, 24]]
    L = [[4, 14, 24, 25], [5, 15, 14, 13], [4, 5, 15, 25], [6, 5, 4, 14]]
    J = [[5, 15, 25, 24], [15, 5, 4, 3], [5, 4, 14, 24], [4, 14, 15, 16]]
    T = [[4, 14, 24, 15], [4, 13, 14, 15], [5, 15, 25, 14], [4, 5, 6, 15]]
    types = {"o": O, "i": I, "s": S, "z": Z, "l": L, "j": J, "t": T}
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.board = [["-" for _ in range(y)] for _ in range(x)]

    def change(self, cords, add=True):
        for (i, j) in cords:
            self.board[i][j] = "0" if add else "-"

    def game(self):
            n = self.types.get(input().lower(), [])
            rotation = 0
            side = (self.y - 10) // 2
            top = -1
            start = -1
            stop = False
            while True:
                s, r = 0, 0
                new = [i[:] for i in self.board]
                cmd = input() if start != -1 else "down"
                start = 1
                if cmd == "exit":
                    break
                if cmd == "left":
                    s = -1
                elif cmd == "right":
                    s = 1
                elif cmd == "rotate":
                    r = 1
                move = self.index(n[(rotation + r) % len(n)], (side + s), top)
                mx, my = [i[0] for i in move], [i[1] for i in move]
                def cant(cords):
                    for (i, j) in cords:
                        if i not in range(self.x) or j not in range(self.y) or new[i][j] == "0":
                            return True
                    return False

                if max(my) >= self.y or min(my) < 0 or max(mx) >= self.x - 1 or cant(move) or stop:
                    s, r = 0, 0
                side += s
                rotation += r
                if cmd in ("left", "right", "rotate","down"):
                    top += 1
                    move = self.index(n[rotation % len(n)], side, top)
                    if cant(move):
                        top -= 1
                        stop = True
                        move = self.index(n[rotation % len(n)], side, top)
                for (i, j) in move:

                    if i in range(self.x) and j in range(self.y):
                        new[i][j] = "0"
                if cmd == "piece":
                    self.board = new
                    self.game()
                    return
                if cmd == "break":
                    dis = ["0" for i in range(self.y)]
                    while dis in new:
                       new.remove(dis)
                       new = [["-" for i in range(self.y)]] + new
                       self.board = new
                       self.x -= 1
                self.show(new)
                check = [[new[i][j] for i in range(self.x)] for j in range(self.y)]
                dis = ["0" for i in range(self.x)]
                if dis in check:
                    print()
                    noooo = input()
                    self.show(new)
                    print()
                    print("Game Over!")
                    return
                print()
m, n = input().split()
m, n = int(m), int(n)
board = Teris(n, m)
board.show()
print()
xion = input()
board.game()
