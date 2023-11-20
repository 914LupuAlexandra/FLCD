definition p3(n):
    int s = 0;
    while n:
        int a = input("Enter value: ")
        s = s + a
        n = n - 1
    return s
    
int n = input("Enter n: ")
print(p3(n))