from scapy import *

a = IP(dst=10.32.8.11, src=10.32.132.85)  # 10.32.132.85即为伪造的源ip

b = UDP(dport=53)

c = DNS(id=1, qr=0, opcode=0, tc=0, rd=1, qdcount=1, ancount=0, nscount=0, arcount=0）

c.qd=DNSQR(qname=www.qq.com, qtype=1, qclass=1)

p=a / b / c

send(p)
