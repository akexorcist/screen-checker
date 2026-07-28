package app.akexorcist.checkscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.screenchecker.ScreenCheckerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, ScreenCheckerActivity::class.java))

        finish()
    }
}
