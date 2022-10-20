import random
rating = 0
inp = "rock,paper,scissors"
name = input("Enter your name: ")
print("Hello,", name)
line = input()
if len(line) != 0:
    inp = line
print("Okay, let's start")
txt_file = open("rating.txt", "r")
ratings = txt_file.readlines()
for i in ratings:
    n, s = i.replace("\n", "").split()
    if n == name:
        rating = rating + int(s)
choices = inp.split(",")
loses = {}
half = len(choices) // 2
for i in range(len(choices)):
    a = choices[i + 1: i + half + 1]
    b = choices[0:half - len(a)]
    loses[choices[i]] = a + b
while True:
    user = input()
    option = random.choice(choices)
    output = {"Loss": f"Sorry, but the computer chose {option}", "Draw": f"There is a draw ({option})", "Win": f"Well done. The computer chose {option} and failed"}
    if user == "!rating":
        print(f"Your rating: {rating}")
    elif user == "!exit":
        print("Bye!")
        break
    elif user not in choices:
        print("Invalid input")
    elif user == option:
        print(output["Draw"])
        rating = rating + 50
    elif option in loses[user]:
        print(output["Loss"])
    elif user in loses[option]:
        print(output["Win"])
        rating = rating + 100
