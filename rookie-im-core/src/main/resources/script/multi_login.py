import websocket
import json
import uuid
import threading
import struct
import time

def send_messages(ws):
    # 生成唯一标识符 IMEI
    imei = str(uuid.uuid1())
    userId = 'jaguarliu'

    # 打包基础数据
    command = 9000
    version = 2
    clientType = 5  # MAC
    messageType = 0x0
    appId = 10000

    # 将数据转换为 bytes
    imei_bytes = imei.encode('utf-8')
    imei_length = len(imei_bytes)
    user_data = {"userId": userId}
    body = json.dumps(user_data).encode('utf-8')
    body_length = len(body)

    # 构造消息头部
    header_format = '>5I2I'  # 5个int类型的基础数据，加上2个unsigned int类型的长度数据
    packed_header = struct.pack(header_format, command, version, clientType, messageType, appId, imei_length, body_length)

    # 构建最终的二进制消息
    final_message = packed_header + imei_bytes + body

    # 发送消息
    ws.send(final_message, opcode=websocket.ABNF.OPCODE_BINARY)

def on_message(ws, message):
    print("Received message: " + message)
    command, body_length = struct.unpack('>I4xI', message[:12])  # 假设command和length在消息的开始位置
    if command == 9002:
        print("收到下线通知, 退出登录")
        ws.close()

def on_error(ws, error):
    print("Error: " + str(error))

def on_close(ws):
    print("### closed ###")

def on_open(ws):
    print("Connection opened...")
    # 开启线程发送消息
    threading.Thread(target=send_messages, args=(ws,)).start()

if __name__ == "__main__":
    # 开启 WebSocket 调试信息
    websocket.enableTrace(True)
    ws = websocket.WebSocketApp("ws://127.0.0.1:9000/ws",
                                on_message=on_message,
                                on_error=on_error,
                                on_close=on_close)
    ws.on_open = on_open
    ws.run_forever()
