package ug.ac.ndejje.universitydigitalid

//add a border to the image
//for watermark images
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
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
import androidx.compose.ui.graphics.Color as ComposeColor


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

    val logo = painterResource(R.drawable.ndu_logo)
    val studentPhoto = painterResource(R.drawable.student_photo)
    val flag = painterResource(R.drawable.uganda_flag)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(ComposeColor(0xFF8B1E1E))
        )

        Image(
            painter = logo,
            contentDescription = "Ndejje University Logo",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.TopStart)
                .offset(x = 16.dp, y = 30.dp)
                .clip(CircleShape)
                .background(ComposeColor.White)
                .padding(6.dp)
        )

        Image(
            painter = flag,
            contentDescription = "Uganda Flag",
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-16).dp, y = 16.dp)
        )


        Image(
            painter = studentPhoto,
            contentDescription = "Student Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(130.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .border(4.dp, ComposeColor(0xFF8B1E1E), CircleShape)
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
        Row {
            Text(
                text = "Programme:",
                fontSize = 16.sp
            )
            Text(
                text = stringResource(R.string.programme),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row {
            Text(
                text = "Registration Number: ",
                fontSize = 16.sp
            )
            Text(
                text = stringResource(R.string.reg_number),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row {

            Text(
                text = "Date of Issue: ",
                fontSize = 14.sp
            )
            Text(
                text = "01/02/2026",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors()
    ) {

        Box {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Image(
                    painter = painterResource(R.drawable.ndu_logo),
                    contentDescription = "Watermark Left",
                    modifier = Modifier
                        .size(180.dp)
                        .alpha(0.08f)
                )

                Image(
                    painter = painterResource(R.drawable.ndu_logo),
                    contentDescription = "Watermark Right",
                    modifier = Modifier
                        .size(180.dp)
                        .alpha(0.08f)
                )
            }

            Column{

                StudentHeader()

                StudentDetails()

                BarcodeSection()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(ComposeColor(0xFF8B1E1E))
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = false
)
@Composable
fun PreviewStudentIdCard(){
    StudentIdCard()
}