import socket,ssl
import thread


# Creates socket
ssocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = ''
port =80
print('socket created')

# Listens to address
ssocket.bind((host, port))
ssocket.listen(100)
print('now bound to port 80')

#function for handling sockets
def start_accepting(client):
    try:
        print('new client accepted')
        skipEtiHeader=bytes('')
        tempS=''
        while True:
            skipEtiHeader=client.recv(1)
            tempS+=skipEtiHeader.decode(encoding='utf-8', errors='strict')
            xxx=tempS.find('\r\n\r\n')
            if xxx >=0:
                print(tempS)
                break
        header= bytes('')
        header = client.recv(40)
        y = header.rstrip()
        z = y.decode(encoding='utf-8', errors='strict')
        a = int(z)
        header = client.recv(a)
        h = header.decode(encoding='utf-8', errors='strict')
        print(h)
        subscribers = ['kobi', 'freetrial1','freetrial2','freetrial3']
        y = h.split('*****$$$$$')
        username = y[0]
        if username not in subscribers:
            client.close()
            return
        host = y[1]
        port = y[2]
        fullbodyString = y[3]
        fullbody = bytes(fullbodyString)
        clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        if "443" == port: 
            SSLclientsocket=ssl.wrap_socket(clientsocket)
            SSLclientsocket.connect((host,int(port)))
            SSLclientsocket.send(fullbody)
            response = bytes('')
            while True:
                data = SSLclientsocket.recv(2024)
                lenght = len(data)
                if lenght <= 0:
                    break
                response += data
            client.sendall(response)
            SSLclientsocket.close()
            client.close()
        else:
            clientsocket.connect((host,int(port)))
            clientsocket.send(fullbody)
            response = bytes('')
            while True:
                data = clientsocket.recv(2024)
                lenght = len(data)
                if lenght <= 0:
                    break
                response += data
            client.sendall(response)
            clientsocket.close()
            client.close()
    except Exception as e:
        print('error processing this socket',e)

print ('let the while loop begin')
while True:
    try:
        clientel, addr = ssocket.accept()
        print('new client has been accepted on this port')
        thread.start_new_thread(start_accepting ,(clientel,))
    except:
        print('error creating threads')