package ug.ac.ndejje.universitydigitalid

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudentIdCard()
        }
    }
}

fun generateBarcode(data: String): Bitmap {

    val bitMatrix: BitMatrix =
        MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, 800, 200)

    val width = bitMatrix.width
    val height = bitMatrix.height

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap.setPixel(
                x,
                y,
                if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            )
        }
    }

    return bitmap
}

@Composable
fun StudentHeader() {

    val logo = painterResource(R.drawable.logo)
    val studentPhoto = painterResource(R.drawable.male_student)
    val flag = painterResource(R.drawable.flag)

    Box {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ComposeColor(0xFF8B1E1E))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = logo,
                contentDescription = "University Logo",
                modifier = Modifier.size(70.dp)
            )

            Image(
                painter = flag,
                contentDescription = "Uganda Flag",
                modifier = Modifier.size(60.dp)
            )
        }

        Image(
            painter = studentPhoto,
            contentDescription = "Student Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
        )
    }
}

@Composable
fun StudentDetails() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
    ) {

        Text(
            text = stringResource(R.string.student_name),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.programme),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(R.string.reg_number),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row {

            Text(
                text = "Date of Issue: 01/02/2026",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Expiry Date: 01/02/2029",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BarcodeSection() {

    val barcodeBitmap = generateBarcode("24/2/314/D/307")

    Image(
        bitmap = barcodeBitmap.asImageBitmap(),
        contentDescription = "Generated Barcode",
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp)
    )
}

@Composable
fun StudentIdCard() {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors()
    ) {

        Column {

            StudentHeader()

            StudentDetails()

            BarcodeSection()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCard() {
    StudentIdCard()
}