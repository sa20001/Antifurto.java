# Open cfg_file e create a thread
import ast
from threading import Thread
import time
import paho.mqtt.client as mqtt
import paho.mqtt.publish as publish
import encodings.idna

from os.path import dirname, join
file_cfg = join(dirname(__file__), "cfg.json")

read_file_cfg = open(file_cfg, "r")  # apre il file cfg.json in modalità read (lettura)
file_read_cfg = read_file_cfg.read()  # creo variabile file_read che corrisponde alla lettura del file
read_file_cfg.close()
# print(read_file_cfg)
# print(type(read_file_cfg))


cfg_dict = ast.literal_eval(file_read_cfg)  # converto il la variabile file_read (str) in dizionario
# print(type(cfg_dict))
# print(cfg_dict)

wait_time = cfg_dict["Delay_time"]  # estraggo dal dizionario il valore della chiave delay time
# print(wait_time)

topic_base = cfg_dict["Topic_base"]  # configurazione per connettermi al broker
device_id_main = cfg_dict["Device_id_main"]
device_id_centralina = cfg_dict["Device_id_centralina"]
device_id_check = cfg_dict["Device_id_main_check"]
device_id_alarm = cfg_dict["Device_id_main_alarm"]
device_id_kill = cfg_dict["Device_id_main_kill"]
device_id_error = cfg_dict["Device_id_main_error"]
device_id_error_message = cfg_dict["Device_id_main_error_message"]
broker_server = cfg_dict["Broker_server"]  # |

i = 0  # inizializzo la variabile i a 0

while_trigger = True  # trigger per rendere il ciclo while vero, con stato false serve per sbloccare il thread


def code_My_Thread(nome, attesa):
    while while_trigger:
        global i  # la definisco global
        i += 1  # aumenta di uno ad ogni ciclo
        print(nome + "Number of time program published to Broker: " + str(
            i))  # per controllo, per verificare quante volte ha pubblicato a Broker

        connect_server()
        publish_code(device_id_centralina, cfg_dict) # invio al server il file cfg.json
        time.sleep(attesa)


# definisco il thread
publish_centralina = Thread(target=code_My_Thread,
                            args=("Publish to Broker--> ", wait_time))  # args qui vuole una tupla


def Start_thread_centralina():
    # eseguo il thread
    publish_centralina.start()


def Stop_thread_centralina():
    global while_trigger
    while_trigger = False  # in questo modo rendo il ciclo while false e sblocco il thread
    publish_centralina.join()  # killa il thread

    cfg_dict["Status"] = "Inactive"
    connect_server()
    publish_code(device_id_centralina, cfg_dict) # invio al server il file cfg.json, con stato inactive
    print("Fine esecuzione del thread \"" + device_id_centralina + "\"")  # Messaggio di verifica


def publish_code(device_id_val, cfg_dict_val):
    publish.single(topic_base + device_id_val, payload="Message correctly sent: " + str(cfg_dict_val),
                   hostname=broker_server) # TODO: fixare errore che si presenta come prima linea quando si avvia il programma


def connect_server():
    client = mqtt.Client(client_id="AFP")
    client.connect(broker_server)  # si connette al broker server
