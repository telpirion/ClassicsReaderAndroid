import sys

print ("Hello world")
a, b, c = 1, 2, "john"
print (a)
print (b)
print (c)

tuple = ('abcd', 786, 2.23, c, a)
list = ["efgh", 123, b]

print (tuple)
print (list)

dict = {}
dict["one"] = "This is one"

print (dict["one"])

# f = open("output.txt", "r+")
# print f

# print (f.read())

print (sys.argv[1])