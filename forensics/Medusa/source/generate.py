import qrcode
import funcy
from PIL import Image
import base64

# get bytes from a file 
doc = "/medusa/medusa.pdf"

with open(doc, "rb") as f:
    data = f.read()

# split data into chunks
split_data = funcy.chunks(100, data)

# create qr code frames
frames = []
i = 0

# create qr codes
for b in split_data:
    qr = qrcode.QRCode()
    qr.add_data(base64.b64encode(b))
    qr.make(fit=True)
    img = qr.make_image()
    img.save("frames/" + str(i) + '.png')
    i+=1

# create gif from frames using ffmpeg