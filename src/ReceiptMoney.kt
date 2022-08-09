import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream

private const val FONT = "IRANSansMobile.ttf"


class ReceiptMoney {
    fun createTable(){
        val fontBig = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 7F, Font.NORMAL)
        val fontSmall = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 5F, Font.NORMAL)
        val fontYekan = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)

        val items = ArrayList<String>()
        items.add("۱۰:۵۵:۴۹")
        items.add("۱۳۹۸/۰۸/۲۶")
        items.add("شماره سند")
        items.add("۱۰۹۸۳۰۰۵۸")
        items.add("نام طرف حساب")
        items.add("عمومي -")

        val tableGenerator = TableGenerator()
        val itemsDocument1=ArrayList<Element>()


        itemsDocument1.add(tableGenerator.imageCenter("logokian.png"))

        itemsDocument1.add(tableGenerator.textCenter("متن1", "", fontBig))
        itemsDocument1.add(tableGenerator.textCenter("متن2", "", fontBig))

        var arrayHeaderTable = FloatArray(2)
        arrayHeaderTable[0] = 130F
        arrayHeaderTable[1] = 70F
        val headTable = tableGenerator.headTable("رسید دریافت وجه", items, fontBig, arrayHeaderTable)
        itemsDocument1.add(headTable)


        itemsDocument1.add(tableGenerator.textRightWithBorder("توضیحات دریافتی از عمومی", "-", fontBig))

        val itemsT2 = ArrayList<String>()
        itemsT2.add("نوع")
        itemsT2.add("سریال / کدپیگیری")
        itemsT2.add("کد شناسه")
        itemsT2.add("سر رسید")
        itemsT2.add("بانک")
        itemsT2.add("مبلغ(ریال)")
        itemsT2.add("وجه نقد")
        itemsT2.add("۴۱۰۰۰۰۶")
        itemsT2.add("۱۰۰۰۰")
        itemsT2.add("۱۰۰۰۰")
        itemsT2.add("۱۰۰۰۰")
        itemsT2.add("۴۶۰,۰۰۰")
        itemsT2.add("وجه نقد")
        itemsT2.add("۴۱۰۰۰۰۶")
        itemsT2.add("۱۰۰۰۰")
        itemsT2.add("۱۰۰۰۰")
        itemsT2.add("۱۰۰۰۰")
        itemsT2.add("۴۶۰,۰۰۰")

        var array = FloatArray(6)
        array[0] = 23F
        array[1] = 17F
        array[2] = 20F
        array[3] = 20F
        array[4] = 25F
        array[5] = 15F
        val productTable = tableGenerator.tableProduct("مشخصات دریافتی", itemsT2, fontBig,array)
        itemsDocument1.add(productTable)


        val items3 = ArrayList<String>()
        items3.add("جمع مبالغ نقدی:")
        items3.add("۴۶۰,۰۰۰")
        items3.add("جمع مبالغ کارتخوان:")
        items3.add("۴۶۰,۰۰۰")
        items3.add("جمع مبلغ چک ها:")
        items3.add("۵۰۰,۰۰۰")
        items3.add("جمع دریافت کل:")
        items3.add("۹۶۰,۰۰۰")
        val footerTable = tableGenerator.footerTable(items3, fontBig)
        itemsDocument1.add(footerTable)



        val itemsDocument2=ArrayList<Element>()



        itemsDocument2.add(tableGenerator.textRightWithOutBorder("نام صندوقدار :", "وزیتور پخش", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("متن1", "", fontBig))
        itemsDocument2.add(tableGenerator.textRightWithOutBorder("شاهرود شهرک صنعتي خيابان صنعت 6", "", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("متن2", "", fontBig))

        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))

        itemsDocument2.add(tableGenerator.textRowCenter("مهر و امضا پرداخت کننده", "مهر و امضا دریافت کننده", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))
        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))

        itemsDocument2.add(tableGenerator.textCenterWithBackground("نرم افزار پخش مویرگی تیانا www.nak-it.com", "", fontSmall))

        itemsDocument2.add(tableGenerator.textCenter("", "", fontBig))
        tableGenerator.totalHeight+=20

        val document = Document(Rectangle(226F, tableGenerator.totalHeight), 0F, 0F, 0F, 0F)
        val pdfWriter: PdfWriter = PdfWriter.getInstance(document, FileOutputStream("ReceiptMoney.pdf"))
        pdfWriter.runDirection = PdfWriter.RUN_DIRECTION_RTL;
        document.open()

        itemsDocument1.forEach {
            document.add(it)
        }
        document.add(tableGenerator.barcodeTable("9611453", fontYekan, pdfWriter))

        itemsDocument2.forEach {
            document.add(it)
        }
        document.close()
    }
}