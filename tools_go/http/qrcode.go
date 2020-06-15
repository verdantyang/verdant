package main

import (
	"github.com/tuotoo/qrcode"
	"log"
	"os"
	"time"
)

func main() {
	var logger = log.New(os.Stdout, "\r\n", log.Ldate|log.Ltime|log.Llongfile)
	t1 := time.Now()
	fi, err := os.Open("/Users/verdant/Downloads/QR-CODE-1.jpg")
	if err != nil {
		logger.Println(err.Error())
		return
	}
	defer fi.Close()
	qrmatrix, err := qrcode.Decode(fi)
	if err != nil {
		logger.Println(err.Error())
		return
	}
	elapsed := time.Since(t1)
	logger.Println(qrmatrix.Content, elapsed)
}
