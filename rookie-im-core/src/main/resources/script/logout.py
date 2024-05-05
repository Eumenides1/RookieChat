import websocket
import json
import uuid
import threading
import time
import struct

def send_logout(ws):
    userId = 'jaguarliu'
    appId = 10000
    clientType = 4
    imei = str(uuid.uuid1())

    # 基础数据
    command = 9003  # LOGOUT command
    version = 1
    messageType = 0x0

    # 数据转换为 bytes
    imei_bytes = imei.encode('utf-8')
    imei_length = len(imei_bytes)
    name_bytes = json.dumps({"userId": userId}).encode('utf-8')
    body_length = len(name_bytes)

    # 打包整数为大端序的二进制格式，并包括 imei_length 和 body_length
    header_format = '>5i2I'
    packed_header = struct.pack(header_format, command, version, clientType, messageType, appId, imei_length, body_length)

    # 构建最终的二进制消息
    final_message = packed_header + imei_bytes + name_bytes

    # 发送注销消息
    ws.send(final_message, opcode=websocket.ABNF.OPCODE_BINARY)

def send_messages(ws):
    imei = str(uuid.uuid1())
    userId = 'jaguarliu'

    # 数据转换为 bytes
    imei_bytes = imei.encode('utf-8')
    imei_length = len(imei_bytes)
    name_bytes = json.dumps({"userId": userId}).encode('utf-8')
    body_length = len(name_bytes)

    # 基础数据
    command = 9000  # LOGIN command
    version = 1
    clientType = 4
    messageType = 0x0
    appId = 10000

    # 打包整数为大端序的二进制格式，并包括 imei_length 和 body_length
    header_format = '>5i2I'
    packed_header = struct.pack(header_format, command, version, clientType, messageType, appId, imei_length, body_length)

    # 构建最终的二进制消息
    final_message = packed_header + imei_bytes + name_bytes

    # 发送登录消息
    ws.send(final_message, opcode=websocket.ABNF.OPCODE_BINARY)

    # 等待5秒后发送注销命令
    time.sleep(5)
    send_logout(ws)

def on_message(ws, message):
    print("Received message: " + message)

def on_error(ws, error):
    print("Error: " + str(error))

def on_close(ws, close_status_code, close_msg):
    print("### closed ###")
    print("Close status code:", close_status_code)
    print("Close message:", close_msg)

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
