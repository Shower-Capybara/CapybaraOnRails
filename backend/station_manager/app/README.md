### About

The project core holding the RESTful API as well as the message listener.

REST API is responsible for receiving client http requests to get the state of
the system and update station's configuration. It also contains a WebSocket
that forwards a real time stream of events to any connected clients.

To run the web application:

```shell
make api
```

Message listener is used to listen for commands and some of the events from
different services and forwards them to message bus.

To run the web application:

```shell
make messageListener
```

Run the tests:

```shell
make test
```
