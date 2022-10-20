import random
print("H A N G M A N")
won = 0
lost = 0
while True:
    command = input('Type "play" to play the game, "results" to show the scoreboard, and "exit" to quit: ')
    if command == "play":
        word_list = ["python", "java", "swift", "javascript"]
        ran_word = random.choice(word_list)
        letters = set(ran_word)
        attemp = 8
        answer = list("-" * len(ran_word))
        done = set()
        lives = 8
        def hinter(letter):
            for i in range(len(answer)):
                if ran_word[i] == letter:
                    answer[i] = letter
        while True:
            print()
            print("".join(answer))
            letter = input("Input a letter: ")
            if len(letter) != 1:
                print("Please, input a single letter.")
                continue
            if not letter.islower():
                print("Please, enter a lowercase letter from the English alphabet.")
                continue
            if letter in done:
                print("You've already guessed this letter.")
                continue
            elif letter not in letters:
                print("That letter doesn't appear in the word.")
                lives = lives - 1
            else:
                hinter(letter)
            done.add(letter)
            if answer == list(ran_word):
                won = won + 1
                print(f"You guessed the word {ran_word}!\nYou survived!")
                break
            if lives == 0:
                lost = lost + 1
                print("You lost!")
                break
    if command == "results":
        print(f"You won: {won} times.\nYou lost: {lost} times.")
    if command == "exit":
        break
    continue
