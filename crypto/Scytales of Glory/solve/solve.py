f = open("encrypted.txt", "r")

s = f.read()

def scytale_dec(s, d):
    dec = ""
    w = int(len(s)/d)
    for i in range(d):
        for j in range(w):
            dec += s[i + j*d]
    return dec

for i in range(1,50):
    d = scytale_dec(s, i)
    if d.__contains__("Trojan"):
        print(d)
        print(i)
        break

f.close()
