import numpy as np

p = 160391328325027
q = 239307304476007

e = 5

n = p * q
print(n)
phi_n = (p-1) * (q-1)
print(np.gcd(phi_n, e))

d = pow(e, -1, phi_n)
print((e*d)%phi_n) 
print(d)

m = 10

c = pow(m, e, n)
print(c)

dec = pow(c, d, n)


print(dec)