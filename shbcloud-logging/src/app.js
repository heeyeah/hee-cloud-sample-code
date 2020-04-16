const express = require("express")
const app = express();


app.use((req, res, next) => {

  
  console.log(new Date())
  res.status(200).json({
    message: "hello node js"
  })

  res.end()

})
