import re
def postfixer(line):
    operators = {"+": 1, "-": 1, "*": 2, "/": 2, "^": 3, "(": 0, ")": 0} # i know its wrong but it works
    expression, stack, postfix =list(line + ")")[::-1], ["("], []
    while expression:
        ch = expression.pop()
        if ch not in operators:
            while expression and expression[-1] not in operators:
                ch += expression.pop()
            postfix.append(ch)
        elif ch == ")":
            while stack and stack[-1] != "(": 
                postfix.append(stack.pop())
            stack.pop()
        else:
            while ch != "(" and stack and operators[stack[-1]]  >= operators[ch]:
                postfix.append(stack.pop())
            stack.append(ch)
    return postfix
def solution(postfix):
    operatoion = { "+": lambda a, b: a + b,  "-": lambda a, b: a - b, "*": lambda a, b: a * b, "/": lambda a, b: a / b, "^": lambda a, b: a ** b }
    postfix, stack = postfix, []
    for i in postfix:
        if i in operatoion:
            b = int(stack.pop()) 
            a = int(stack.pop())
            stack.append(operatoion[i](a, b))
        else:
            stack.append(i)
    return stack[0]
def var(pair = None, x = {"ab": "1"}):
    if pair:
        i, j = pair
        x[i] = j
    return x
def rep(i, dic):
    for (k, v) in dic.items():
        while(k in i):
            i = i.replace(k, v)
    return i
def magic(x):
    i = x[x.find("=") + 1:]
    l = i
    k = var()
    j = re.findall("\w+", i)
    for m in j:
        i = i.replace(m, k.get(m, m))
    return x.replace("=" + l,"=" + i) if ("=" in x) else x.replace(l, i)
def ans(line):
    line = rep(line, {"--": "+", "++": "+", "+-": "-"}).replace(" ", "")
    return solution(postfixer(line))
while True:
    x = input().replace(" ", "")
    if x == "/exit":
        print("Bye!")
        break
    elif x.startswith("/"):
        if x == "/help":
            print("The program calculates the sum of numbers")
        else:
            print("Unknown command")
    elif "=" in x: #   define part
        if re.match("[a-zA-Z]+=", x):
            x = magic(x)
            if re.fullmatch("[a-zA-Z]+=[+-]?\d+", x):
                var(x.split("="))
            else:
                print("Invalid assignment")
        else:
            print("Invalid identifier")
    elif x:
        x = magic(x)
        if  re.search("[^^\d/( )*+-]", x):
            print("Unknown variable")
        else:
            try:
                if x.count("(") != x.count(")"):
                    raise Exception
                    
                print(ans(x))
            except Exception:
                print("Invalid expression")
