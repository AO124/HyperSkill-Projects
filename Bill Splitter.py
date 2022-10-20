import random
print("Enter the number of friends joining (including you):")
num = int(input())
if num > 0:
    print("Enter the name of every friend (including you), each on a new line:")
    people = []
    dic = {}
    for i in range(num):
        people.append(input())
    print("Enter the total bill value:")
    bill = input()
    print('Do you want to use the "Who is lucky?" feature? Write Yes/No:')
    lucky = input()
    if lucky == "Yes":
        lucky = random.choice(people)
        print(f"{lucky} is the lucky one!")
        bill = round(int(bill) / (num - 1), 2)
        dic = dic.fromkeys(people, bill)
        dic[lucky] = 0
    else:
        print("No one is going to be lucky")
        bill = round(int(bill) / num, 2)
        dic = dic.fromkeys(people, bill)
    print(dic)
else:
    print("No one is joining for the party")
