package com.gitlab.jameshnsears.postcard.edgedetection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Environment
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

open class EdgeDetectionUtils {
    fun makeBitmapTransparent(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, 1 * width, 0, 0, width, height)
        for (pixelIndices in pixels.indices) {
            if (pixels[pixelIndices] == Color.BLACK) pixels[pixelIndices] = 0
        }
        val transparentBitmap = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
        transparentBitmap.setHasAlpha(true)

        return transparentBitmap
    }

    fun saveBitmap(bitmap: Bitmap, filename: String) {
        // as .png into /sdcard/Pictures
        val root = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        ).toString()
        val myDir = File("$root/PostCard")
        if (myDir.delete()) {
            myDir.delete()
        }
        myDir.mkdirs()
        val file = File(myDir, filename)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.flush()
        out.close()
    }

    fun convertMatToBitmap(matImage: Mat): Bitmap {
        val imageAsBitmap =
            Bitmap.createBitmap(matImage.cols(), matImage.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(matImage, imageAsBitmap)
        return imageAsBitmap
    }

    fun convertOriginalImageToBitmap(originalImage: InputStream): Mat {
        val originalImageAsMat = Mat()
        Utils.bitmapToMat(BitmapFactory.decodeStream(originalImage), originalImageAsMat)
        return originalImageAsMat
    }

    // TODO ui filter for Size params
    fun applyGaussianBlurFilterToReduceNoise(imageToBlur: Mat): Mat {
        // https://docs.opencv.org/3.4.15/d4/d86/group__imgproc__filter.html#gaabe8c836e97159a9193fb0b11ac52cf1
        val blurredImage = Mat()
        Imgproc.GaussianBlur(imageToBlur, blurredImage, Size(3.0, 3.0), 0.0, 0.0)
        return blurredImage
    }
}