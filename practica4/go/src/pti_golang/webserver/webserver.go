package main

import (
	"fmt"
	"log"
	"net/http"
	"github.com/gorilla/mux"
	"encoding/json"
	"io"
	"io/ioutil"
	"encoding/csv"
	"os"
	"bufio"
	"strings"
) 

type CarRentalRequest struct {
	CarMaker string
	CarModel string
	NumberDays string
	NumberUnits string
	Prize string
}

func main() {
	router := mux.NewRouter().StrictSlash(true)
	router.HandleFunc("/", Index)
	router.HandleFunc("/newrental/{param}", newrentalFunc)
	router.HandleFunc("/listrentals/", listrentalsFunc)

	log.Fatal(http.ListenAndServe(":8080", router))
}


func Index(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintln(w, "Service OK")
}

func newrentalFunc (w http.ResponseWriter, r *http.Request) {
	var requestMessage CarRentalRequest
    body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
    if err != nil {
        panic(err)
    }
    if err := r.Body.Close(); err != nil {
        panic(err)
    }
    if err := json.Unmarshal(body, &requestMessage); err != nil {
        w.Header().Set("Content-Type", "application/json; charset=UTF-8")
        w.WriteHeader(422) // unprocessable entity
        if err := json.NewEncoder(w).Encode(err); err != nil {
            panic(err)
        }
    } else {
        fmt.Fprintln(w, "Successfully received request for new car rental with name", r.FormValue("name"))
        fmt.Println("Car maker: ", requestMessage.CarMaker)
        fmt.Println("Car model: ", requestMessage.CarModel)
        fmt.Println("Number of days: ", requestMessage.NumberDays)
        fmt.Println("Number of units: ", requestMessage.NumberUnits)
        fmt.Println("Preu: ", 10000)
        requestMessage.Prize = "10000"
        writeToFile(w, requestMessage)
    }
}

func listrentalsFunc (w http.ResponseWriter, r *http.Request) {
	file, err := os.Open("rentals.csv")
    if err!=nil {
		json.NewEncoder(w).Encode(err)
		fmt.Fprintf(w, "Try ordering some rentals before you list them :)")
		return
    }
    reader := csv.NewReader(bufio.NewReader(file))
    //var rentals []CarRentalRequest
    fmt.Fprintln(w, "LIST OF RENTALS")
    for {
        record, err := reader.Read()
        if err == io.EOF {
			break
		}
		rental := strings.Split(record[0], ",")
		fmt.Fprintln(w, "Car maker: ", rental[0])
		fmt.Fprintln(w, "Car model: ", rental[1])
		fmt.Fprintln(w, "Number of days: ", rental[2])
		fmt.Fprintln(w, "Number of units: ", rental[3])
		fmt.Fprintln(w, "Prize: ", rental[4])
		fmt.Fprintln(w, "---------------------")
    }
}

func writeToFile(w http.ResponseWriter, m CarRentalRequest) {
    file, err := os.OpenFile("rentals.csv", os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0600)
    if err!=nil {
        json.NewEncoder(w).Encode(err)
        return
    }
    writer := csv.NewWriter(file)
    var data1 = []string{m.CarMaker + ", " + m.CarModel + ", " + m.NumberDays + ", " + m.NumberUnits + ", " + m.Prize}
    writer.Write(data1)
    writer.Flush()
    file.Close()
}


