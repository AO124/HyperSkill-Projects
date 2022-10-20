import random
# rule = "take 1, 2 or 3 pencils the one who take the last pencil loss"
# print(rule)
print("How many pencils would you like to use:")
pencils = input()
while not pencils.isnumeric() or int(pencils) <= 0:
    if not pencils.isnumeric():
        print("The number of pencils should be numeric")
    else:
        print("The number of pencils should be positive")
    pencils = input()
pencils = int(pencils)
print("Who will be the first (Dedar, Sudipto):")  # Dedar and Sudipto are the dogs I own :-)
first = input()
while first not in ["Dedar", "Sudipto"]:
    print("Choose between 'Dedar' and 'Sudipto'")
    first = input()
second = "Sudipto" if first == "Dedar" else "Dedar"
print('|' * pencils)
turn = 0
while True:
    who = first if turn % 2 == 0 else second
    print(who + "'s turn")
    take = ""
    if who == "Dedar":
        take = input()
        while take not in ["1", "2", "3"] or int(take) > pencils:
            if take not in ["1", "2", "3"]:
                print("Possible values: '1', '2' or '3'")
            else:
                print("Too many pencils were taken")
            take = input()
    else:
        if (pencils - 1) % 4 != 0:
            take = str((pencils - 1) % 4)  # win case 1
        else:
            if pencils == 1:
                take = "1"
            else:
                take = random.choice("123")
        print(take)
    pencils -= int(take)
    if pencils == 0:
        winner = second if turn % 2 == 0 else first
        print(winner + " won!")
        break
    else:
        print('|' * pencils)
        turn += 1
