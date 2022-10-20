msg_ = ["Enter an equation", "Do you even know what numbers are? Stay focused!", "Yes ... an interesting math operation. You've slept through all classes, haven't you?", "Yeah... division by zero. Smart move...", "Do you want to store the result? (y / n):", "Do you want to continue calculations? (y / n):", " ... lazy", " ... very lazy", " ... very, very lazy", "You are", "Are you sure? It is only one digit! (y / n)", "Don't be silly! It's just one number! Add to the memory? (y / n)", "Last chance! Do you really want to embarrass yourself? (y / n)"]
opers = ["+", "-", "/", "*"]
memory = 0
def is_one_digit(v):
    return True if -10 < v < 10 and v.is_integer() else False
def check(v1, v2, v3):
    msg = ""
    if is_one_digit(v1) and is_one_digit(v2):
        msg = msg + msg_[6]
    if (v1 == 1 or v2 == 1) and v3 == "*":
        msg = msg + msg_[7]
    if (v1 == 0 or v2 == 0) and v3 != "/":
        msg = msg + msg_[8]
    if msg != "":
        msg = msg_[9] + msg
        print(msg)
while True:
    continue_or_not = "n"
    print(msg_[0])
    calc = input()
    x, oper, y = calc.split()
    try:
        if x == "M":
            x = memory
        if y == "M":
            y = memory
        x = float(x)
        y = float(y)
    except ValueError:
        print(msg_[1])
        continue
    else:
        if oper not in opers:
            print(msg_[2])
            continue
        else:
            check(x, y, oper)
            result = ""
            if oper == '+':
                result = x + y
            elif oper == '-':
                result = x - y
            elif oper == '*':
                result = x * y
            elif oper == '/' and y != 0:
                result = x / y
            else:
                print(msg_[3])
                continue
            print(result)
            while True:
                print(msg_[4])
                answer = input()
                if answer == "y":
                    if is_one_digit(result) == True:
                        msg_index = 10
                        while True:
                            print(msg_[msg_index])
                            answer = input()
                            if answer == "y":
                                if msg_index < 12:
                                    msg_index += 1
                                    continue
                                else:
                                    memory = result
                                    break
                            if answer != "n":
                                continue
                            break
                    else:
                        memory = result                
                elif answer != "n":
                    continue
                break
            while True:
                print(msg_[5])
                answer = input()
                if answer == "y":
                    continue_or_not = "y"
                elif answer != "n":
                    continue
                break
    if continue_or_not == "y":
        continue
    else:
        break
