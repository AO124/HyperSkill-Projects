import random
msg = ["simple operations with numbers 2-9", "integral squares of 11-29"]
while True:
    global level
    print(f"Which level do you want? Enter a number:\n1 - {msg[0]}\n2 - {msg[1]}")
    level = input()
    if level in ['1', '2']:
        break
    else:
        print("Incorrect format.")
        continue
times = 0
result = 0
def problem2():
    num = random.randint(11,29)
    print(num)
    return num ** 2
def problem1():
    eq = f"{random.randint(2,9)} {random.choice('+-*')} {random.randint(2,9)}"
    print(eq)
    a = eq.split()
    if a[1] == "*":
        a = int(a[0]) * int(a[2])
    elif a[1] == "/":
        a = int(a[0]) / int(a[2])
    elif a[1] == "+":
        a = int(a[0]) + int(a[2])
    elif a[1] == "-":
        a = int(a[0]) - int(a[2])
    return a
while times < 5:
    pro = problem1() if level == "1" else problem2()
    while True:
        try:
            global ans
            ans = int(input())
            break
        except ValueError:
            print("Incorrect format.")
    if ans == pro:
        print("Right!")
        result += 1
    else: 
        print("Wrong!")
    times += 1
print(f"Your mark is {result}/5.")
print("Would you like to save your result to the file? Enter yes or no.")
save = input()
if save in ["yes", "YES", "y", "Yes"]:
    print("What is your name?")
    name = input()
    txt = open("results.txt", "a")
    print(f"{name}: {result}/5 in level {level} ({msg[int(level) - 1]})", file=txt)
    txt.close()
    print('The results are saved in "results.txt".')
