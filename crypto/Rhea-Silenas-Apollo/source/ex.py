import math 
# step 1
p = 359070709489757290463452213
q = 724345929093008319709439533
 
# step 2
n = p*q
print("n =", n)
 
# step 3
phi = (p-1)*(q-1)
 
# step 4
e = 2
while(e<phi):
    if (math.gcd(e, phi) == 1):
        break
    else:
        e += 1
 
print("e =", e)
# step 5
k = 2
d = pow(e, -1, phi)
print("d =", d)
print(f'Public key: {e, n}')
print(f'Private key: {d, n}')
 
# plain text
msg = 28735829315484350823510000961603627086461
print(f'Original message:{msg}')
 
# encryption
C = pow(msg, e, n)

print(f'Encrypted message: {C}')
 
# decryption
M = pow(C, d, n)

 
print(f'Decrypted message: {M}')     