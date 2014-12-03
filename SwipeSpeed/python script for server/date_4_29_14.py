'''
Created on Apr 29, 2014

@author: Dankobs
'''
import socket,ssl
import thread,sys


class classTest:
    def __init__(self):
        # Creates socket
        ssocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        host = ''
        port =80

        # Listens to address
        ssocket.bind((host, port))
        ssocket.listen(100)

        while True:
            try:
                clientel, addr = ssocket.accept()
                #threading.Thread(target=self.start_accepting(clientel)).start()
                thread.start_new_thread ( self.start_accepting,(clientel,) )
            except Exception as e:
                pass

    #function for handling sockets
    def start_accepting(self,client):
        try:
            EtiHeader=bytes('')
            tempS=''
            while True:
                EtiHeader=client.recv(1)
                tempS+=EtiHeader.decode(encoding='utf-8', errors='strict')
                xxx=tempS.find('\r\n\r\n')
                if xxx >=0:
                    break
            yyy=tempS.find('X-ACME-Val: ')
            yyy+=12;
            myInfo=tempS[yyy:-23]
            myInfoByte=myInfo.encode(encoding='utf-8')
            byteInfoList=list(bytes(myInfoByte))
            myinfoStr=''
            for valIB in byteInfoList:
                tempInt=ord(valIB)-5
                myinfoStr+=chr(tempInt)
            hh=myinfoStr.split('*****$$$$$')
            username = hh[0]
            subscribers = ['kobi', 'freetrial1','freetrial2','freetrial3']
            if username not in subscribers:
                client.close()
                return
            host=hh[1]
            port=hh[2]
            fullbodyString = hh[3]
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
            pass

xstart=classTest()
