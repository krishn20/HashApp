package com.example.hashapp

import android.util.Log
import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel: ViewModel()
{
    fun getHash(text: String, algorithm: String): String
    {
        //This MessageDigest class provides applications the functionality of a message digest algorithm, such as SHA-1 or SHA-256.
        // Message digests are secure one-way hash functions that take arbitrary-sized data and output a fixed-length hash value.

        //toByteArray() - Encodes the contents of this string using the specified character set and returns the resulting byte array.
        //getInstance() - Returns a MessageDigest object that implements the specified digest algorithm.

        val bytes = MessageDigest.getInstance(algorithm).digest(text.toByteArray())
        return toHex(bytes)
    }

    private fun toHex(byteArray: ByteArray): String
    {

        //joinToString() basically this converts every character into the hash value and joins these hashes as Strings together (but comma separated still...).
        //For that we use the "separator" field and just leave it empty. This means we will remove any separator and join these hashes as one string together.

        //"%02x" -> %x means hexadecimal. 0 means extra padding needed if numbers are not shown. 2 means the actual length of the hex hash.
        Log.d("ViewModel", byteArray.joinToString("") { "%02x".format(it) })

        return byteArray.joinToString("") {
            "%02x".format(it)
        }
    }

}