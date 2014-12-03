import socket
import _thread


# Creates socket
ssocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = ''
port = 54347

# Listens to address
ssocket.bind((host, port))
ssocket.listen(100)

# Database of usernames
#Database = ["kobi", daniel, phil,]
# Accepts connection

def start_accepting(client):
        try:
            print('new request')

            header= bytes('', 'UTF-8')

            header = client.recv(40)
            l = len(header)
            
            y = header.rstrip()
            z = y.decode(encoding='utf-8', errors='strict')
            print(z)

            a = int(z)

            header = client.recv(a)
            h = header.decode(encoding='utf-8', errors='strict')
            
            
            print (h)

            y = h.split('*****$$$$$')
            print(y)
            host = y[1]
            port = y[2]
            fullbodyString = y[3]
            fullbody = bytes(fullbodyString, 'UTF-8')
            
            clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            clientsocket.connect((host,int(port)))
            clientsocket.send(fullbody)

            response = bytes('', 'UTF-8')
            while True:
                data = clientsocket.recv(2024)
                lenght = len(data)
                if lenght <= 0:
                    break
                response += data
                print('receiving rhema')

            client.sendall(response)
            print('replied server')
            
            clientsocket.close()
            client.close()

        except:
            print('********************************************  \nerror processing socket')

        
while True:
    try:
        clientel, addr = ssocket.accept()
        #threading.Thread(target=start_accepting(clientel)).start()
        _thread.start_new_thread(start_accepting ,(clientel,))
        print('started new thread for client')
    except:
        print('##############################################  \nerror accepting socket')
