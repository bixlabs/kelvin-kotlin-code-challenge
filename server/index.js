const { startSocketServer, startHttpServer } = require('./src/app')

require('dotenv').config()

startHttpServer()
startSocketServer()


