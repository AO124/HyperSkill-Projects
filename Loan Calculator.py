import math
import argparse
def number_of_months(principal, payment, interest):
    p = float(principal)
    a = float(payment)
    i = float(interest) / 1200
    if p < 0 or a < 0 or i < 0:
        print("Incorrect parameters.")
        return
    n = math.ceil(math.log(a / (a - i * p), 1 + i))
    ans = ""
    y = "year" if n / 12 == 1 else "years"
    m = "month" if n % 12 == 1 else "months"
    if n > 11:
        if n % 12 == 0:
            ans = f"It will take {n // 12} {y} to repay this loan!"
        else:
            ans = f"It will take {n // 12} {y} and {n % 12} {m} to repay this loan!"
    else:
        ans = f"It will take {n} {m} to repay this loan!"
    print(ans)
    print(f"Overpayment = {round(a * n - p)}")
def annuity(principal, periods, interest):
    p = float(principal)
    n = int(periods)
    i = float(interest) / 1200
    if p < 0 or n < 0 or i < 0:
        print("Incorrect parameters.")
        return
    a = p * ((i * ((1 + i) ** n)) / ((1 + i) ** n - 1))
    a = math.ceil(a)
    print(f"Your monthly payment = {a}!")
    print(f"Overpayment = {round((a * n) - p)}")
def diff(payment, periods, interest):
    p = float(payment)
    n = int(periods)
    i = float(interest) / 1200
    if p < 0 or n < 0 or i < 0:
        print("Incorrect parameters.")
        return
    overall = round(p)
    for m in range(1,n + 1):
        dm = p / n + i * (p - (m - 1) * p / n)
        dm = math.ceil(dm)
        overall -= dm
        print(f"Month {m}: payment is {dm}")
    print(f"Overpayment = {abs(overall)}")
def loan(payment, periods, interest):
    a = float(payment)
    n = int(periods)
    i = float(interest) / 1200
    if a < 0 or n < 0 or i < 0:
        print("Incorrect parameters.")
        return
    p = a / ((i * ((1 + i) ** n)) / (((1 + i) ** n) - 1))
    p = math.floor(p)
    print(f"Your loan principal = {p}!")
    print(f"Overpayment = {round(a * n) - p}")
        
parser = argparse.ArgumentParser(description="I was super hard")
parser.add_argument("--type")
parser.add_argument("--payment")
parser.add_argument("--principal")
parser.add_argument("--periods")
parser.add_argument("--interest")

args = parser.parse_args()
if args.type == "diff":
    if args.principal and args.periods and args.interest:
        diff(args.principal, args.periods, args.interest)
    else:
        print("Incorrect parameters.")
        
elif args.type == "annuity":
    if args.principal and args.payment and args.interest:
        number_of_months(args.principal, args.payment, args.interest)
    elif args.payment and args.periods and args.interest:
        loan(args.payment, args.periods, args.interest)
    elif args.principal and args.periods and args.interest:
        annuity(args.principal, args.periods, args.interest)
    else:
        print("Incorrect parameters.")
    
else:
    print("Incorrect parameters.")
