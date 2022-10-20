machine = {"water": 400, "milk": 540, "beans": 120, "cups": 9, "money": 550}
per_cup = {"water": [250, 350, 200], "milk": [0, 75, 100], "beans": [16, 20, 12], "cups": [1, 1, 1], "money": [-4, -7, -6]}
items = ["water", "milk", "beans", "cups", "money"]
def status():
    msg_s = ["The coffee machine has:",f"{machine['water']} ml of water", f"{machine['milk']} ml of milk", f"{machine['beans']} g of coffee beans", f"{machine['cups']} disposable cups", f"${machine['money']} of money"]
    print("\n".join(msg_s))
def buy():
    print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: :")
    coffee = input()
    if coffee == "back":
        return
    elif coffee in ["1", "2", "3"]:
        coffee = int(coffee) - 1
    for i in items:
        if machine[i] < per_cup[i][coffee]:
            print(f"Sorry, not enough {i}!")
            return
    print("I have enough resources, making you a coffee!")
    for i in items:
        machine[i] -= per_cup[i][coffee]
    
def fill():
    msg_f = ["Write how many ml of water you want to add: ", "Write how many ml of milk you want to add: ", "Write how many grams of coffee beans you want to add: ", "Write how many disposable cups you want to add: "]
    for i in range(len(msg_f)):
        print(msg_f[i])
        machine[items[i]] += int(input())
def take():
    print(f"I gave you ${machine['money']}")
    machine["money"] = 0
while True:
    print("\nWrite action (buy, fill, take, remaining, exit): ")
    task = input()
    if task == "buy":
        buy()
    elif task == "fill":
        fill()
    elif task == "take":
        take()
    elif task == "remaining":
        status()
    elif task == "exit":
        break
