f = open("decrypted.txt", "r")
out = open("encrypted.txt", 'w')
s = f.read()
print(len(s))
def scytale_dec(s, d):
    dec = ""
    w = int(len(s)/d)
    for i in range(d):
        for j in range(w):
            dec += s[i + j*d]
    return dec

def scytale_enc(s,d):
    enc = ""
    w = int(len(s)/d)
    for i in range(w):
        for j in range(d):
            enc += s[i + j*w]
    return enc

print(len(s))
enc = scytale_enc(s, 4)
print(enc)
dec = scytale_dec(enc, 4)
print(dec)
out.write(enc)
out.close()
f.close()

