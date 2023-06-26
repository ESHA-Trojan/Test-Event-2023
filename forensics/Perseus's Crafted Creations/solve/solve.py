from scapy.all import *

import struct

from PIL import Image

# https://gist.github.com/nickelpro/7312782
def unpack_varint(buff):
    total = 0
    shift = 0
    val = 0x80
    read = 0
    while val&0x80:
        val = struct.unpack('B', buff[read:read+1])[0]
        read += 1
        total |= ((val&0x7F)<<shift)
        shift += 7
    if total&(1<<31):
        total = total - (1<<32)
    return (total, read)

# https://stackoverflow.com/questions/1604464/twos-complement-in-python
def twos_comp(val, bits):
    """compute the 2's complement of int value val"""
    if (val & (1 << (bits - 1))) != 0: # if sign bit is set e.g., 8bit: 128-255
        val = val - (1 << bits)        # compute negative value
    return val   

# see https://wiki.vg/Protocol#Position, but done in a scuffed way
def unpack_position(buff):
    bits = []
    for byte in buff:
        for i in range(7, -1, -1):
            bits.append('1' if (byte & (2**i) != 0) else '0')
    
    # take the right bit ranges for each coordinate
    x_b = ''.join(bits[0:26])
    x = twos_comp(int(x_b,2), len(x_b))
    
    z_b = ''.join(bits[26:52])
    z = twos_comp(int(z_b,2), len(z_b))
    
    y_b = ''.join(bits[52:64])
    y = twos_comp(int(y_b,2), len(y_b))
    
    return x, y, z

positions = []

def read_packet(b):
    global positions
    
    # see https://wiki.vg/Protocol#With_compression
    p_length, read = unpack_varint(b)
    b = b[read:]

    d_length, read = unpack_varint(b)
    b = b[read:]

    p_id, read = unpack_varint(b)
    b = b[read:]

    if p_id == 0x05: # chat packet
        print(f"Chat packet: {b}")
    if p_id == 0x31: # use item on packet, used for placing blocks
        print("Use Item On packet")
        # see https://wiki.vg/Protocol#Use_Item_On
        hand, read = unpack_varint(b)
        b = b[read:]

        position_b = b[:8]
        position = unpack_position(position_b)
        
        print(position)
        positions.append(position)

packets = rdpcap('traffic.pcapng')
for packet in packets:
    tcp = packet[TCP]
    if tcp.dport == 25565 and tcp.flags.P:
        # filter on Minecraft packets sent from client to server
        b = bytes(tcp.payload)
        read_packet(b)

# get the bounds in which the build resides
min_x = min([x for x,y,z in positions])
max_x = max([x for x,y,z in positions])
min_y = min([y for x,y,z in positions])
max_y = max([y for x,y,z in positions])
min_z = min([z for x,y,z in positions])
max_z = max([z for x,y,z in positions])

print(f"X= {min_x}:{max_x}; Y= {min_y}:{max_y}; Z= {min_z}:{max_z}")

# hardcoded the fact that the Z coord is fixed while X and Y are variable (we already knew it was 2D from chat msgs)

im = Image.new("RGB", (max_x - min_x + 1, max_y - min_y + 1))
for position in positions:
    # get relative coords
    x = position[0]-min_x
    y = position[1]-min_y
    
    # make that pixel white
    im.putpixel((x, y), (255, 255, 255))
im.show()
