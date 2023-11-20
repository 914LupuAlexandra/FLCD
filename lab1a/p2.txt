definition p2(int a, int b):
    if a == 0:
        return b
    if b == 0:
        return a
    if a == b:
        return a
    if a greater than b:
        return p2(a-b, b)
    else:
        return p2(a, b-a)
    
int a = input("Enter a: "))
int b = input("Enter b: "))
print(p2(a, b))