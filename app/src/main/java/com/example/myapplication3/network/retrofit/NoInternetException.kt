package com.example.myapplication3.network.retrofit

class NoInternetException : RuntimeException {

    constructor() : super("No Internet available")
}