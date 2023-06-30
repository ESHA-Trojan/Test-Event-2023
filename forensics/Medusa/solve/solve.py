import imageio
from pyzbar.pyzbar import decode
from PIL import Image
import base64

# get image frames
gif_file = "/medusa/message.gif"
reader = imageio.get_reader(gif_file)

# create string for data
qr_data = b""

# iterate through each frame and extract the QR code data
for frame in reader:
    decocdeQR = decode(frame)
    qr_data += base64.b64decode(decocdeQR[0].data.decode('ascii'))

# write the bytes to a file
with open("test/medusaa", "wb") as f:
    f.write(qr_data)