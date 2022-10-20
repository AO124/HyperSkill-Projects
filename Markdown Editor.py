ans = ""
def plain():
    text = input("Text: ")
    return text
def list():
    listo = []
    r = None
    while True:
           r = input("Number of rows: ")
           if r.isnumeric() and int(r) > 0:
               r = int(r)
               break
           else:
               print("The number of rows should be greater than zero")
    for i in range(1, r + 1):
        n = (str(i) + ". ") if formatter == "ordered-list" else "* "
        listo.append(f"{n}{input(f'Row #{i}: ')}")
        
    return "\n".join(listo) + "\n"
def bold():
    text = input("Text: ")
    return "**" + text + "**"
    
def italic():
    text = input("Text: ")
    return "*" + text + "*"
    
def header():
    level = None
    while True:
        level = input("Level: ")
        if level.isnumeric() and int(level) in range(1, 7):
            level = int(level)
            break
        else:
            print("The level should be within the range of 1 to 6")
    text = input("Text: ")
    return f"{'#' * level} {text}\n"
    
def link():
    label = input("Label: ")
    url = input("URL: ")
    return f"[{label}]({url})"
def inline_code():
    text = input("Text: ")
    return "`" + text + "`"
def new_line():
    return "\n"   


formatters = {"plain": plain, "bold": bold, "italic": italic, "header": header, "link":link, "inline-code": inline_code, "new-line": new_line, "ordered-list": list, "unordered-list": list}
while True:
    global formatter
    formatter = input("Choose a formatter: ")
    if formatter == "!help":
        print("Available formatters:", *formatters)
        print("Special commands: !help !done")
    elif formatter == "!done":
        with open("output.md", "w") as md:
            md.write(ans)

        break
    elif formatter not in formatters:
        print("Unknown formatting type or command")
    else:
       ans +=  formatters[formatter]()
       print(ans)
