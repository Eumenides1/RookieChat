import websocket
import json
import uuid
import threading
import time
import struct

def send_messages(ws):
    imei = str(uuid.uuid1())
    userId = 'jaguarliu'

    # 数据转换为 bytes
    imei_bytes = imei.encode('utf-8')
    imei_length = len(imei_bytes)
    name_bytes = json.dumps({"userId": userId}).encode('utf-8')
    body_length = len(name_bytes)

    # 基础数据
    command = 9000
    version = 1
    clientType = 4
    messageType = 0x0
    appId = 10000

    # 打包整数为大端序的二进制格式，并包括 imei_length 和 body_length
    header_format = '>5i2I'  # 5个int类型的基础数据，加上2个unsigned int类型的长度数据
    packed_header = struct.pack(header_format, command, version, clientType, messageType, appId, imei_length, body_length)

    # 构建最终的二进制消息
    final_message = packed_header + imei_bytes + name_bytes

    # 发送消息

    ws.send(final_message, opcode=websocket.ABNF.OPCODE_BINARY)

def on_message(ws, message):
    print("Received message: " + message)

def on_error(ws, error):
    print("Error: " + str(error))

def on_close(ws):
    print("### closed ###")

def on_open(ws):
    print("Connection opened...")
    threading.Thread(target=send_messages, args=(ws,)).start()

if __name__ == "__main__":
    websocket.enableTrace(True)
    ws = websocket.WebSocketApp("ws://127.0.0.1:9000/ws",
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()
