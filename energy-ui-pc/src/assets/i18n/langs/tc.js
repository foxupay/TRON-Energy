
import zhLocale from 'element-ui/lib/locale/lang/zh-CN' //引入element语言包
//需要转换的中文
const tc = {
  message: {
    info: {
      Number_Box:'號碼寶盒接碼平臺（美國號碼）',
      Code_connection: '接碼',
      log_on: '登入',
      balance:'餘額',
      download: 'IOS下載',
      Android_Download: '安卓下載',
      Please_enter_content: '請輸入內容',
      search_for: '蒐索',
      Recharge: '充值',
      Instructions_for_use: '使用說明（充值前必看）',
      Note: '注意：WhatsApp風控中，不保證接碼率； Google接碼沒有科技不要買。 以上這兩個項目的酌情充值取碼。',
      Customer_service_email: '客服郵箱',
      PROJECT_NO: '項目號',
      item_name: '項目名',
      Number: '剩餘號碼數',
      Cost: '費用/每次',
      operate: '操作',
      Number_retrieval: '取號取號',
      previous_page: '上一頁',
      next_page: '下一頁',
      Recharge:'充值',
      Recharge_code: '充值金額',
      Please_code: '請輸入充值金額',
      Go_code: '去購買充值碼',
      cancellation:'取消',
      determine:'確定',
      Get_Number: '獲取號碼',
      Code_phone: '接碼手機號',
      Click_below: '點擊下方獲取',
      Code: '驗證碼',
      Stop_code: '停止接碼',
      log_in: '用戶登錄',
      Please_name: '請輸入帳戶名',
      Please_password: '請輸入密碼',
      Register_Now: '立即注册',
      User_Registration: '用戶註冊',
      Please_the_password: '請確認輸入密碼',
      register: '注册',
      zz1: '餘額不足請先充值',
      zz2: "正在請求中...",
      zz3: "沒有更多了",
      zz4: "請輸入帳號",
      zz5: "請輸入密碼",
      zz6: "長度限制在6~20比特之間，必須包含字母和數位",
      zz7:"兩次密碼不一致"
   
    },
    shuoming: {
      p1:'這裡有一些常見的問答，客服工作量大，回復不會永遠都那麼及時。 你可以看了這頁的內容後再諮詢客服。',
      p2:'Q：有哪些國家的號碼？',
      p3:'A：現時只有+1區號的加拿大和美國的號碼。 不要問有沒有其他國家的，我們只做這個區號的號碼。 很多項目其實沒有那麼嚴格地區別這兩個國家的號碼，比如Telegram接碼的時候，直接填寫號碼就可以，系統自動分辨國別，所以，我們都是將這兩個國家的號碼放在一起。A：現時只有+1區號的加拿大和美國的號碼。 不要問有沒有其他國家的，我們只做這個區號的號碼。 很多項目其實沒有那麼嚴格地區別這兩個國家的號碼，比如Telegram接碼的時候，直接填寫號碼就可以，系統自動分辨國別，所以，我們都是將這兩個國家的號碼放在一起。',
      p4:'Q：都有哪些項目？',
      p5:'A：所有的項目清單都在sms.code877.com上面即時更新。 平臺現時主要做國外項目，也兼做國內項目，但是以後會逐漸减少國內項目。 如果你做的是國內的，請抓緊時間搞，不然哪天我們突然暴富了，會完全看不上國內項目。 另外，使用國內項目URL或者API的話，我們只支持USDT。',
      p6:'Q：是虛擬卡還是實體卡？',
      p7:'A：雖然標榜自己是實體卡是一種溢價的方法，但是我們還是很坦誠地告訴你，我們的卡都是虛擬卡。 如果你是老手的話，看價格也知道。 但是，我們都是出首碼，所以接碼率特別高。 有些App接不到，是因為他們本身風控的原因，比如WhatsApp和PayPal。 市面上所謂的實體卡接碼，都是用了N遍且接碼率低得離譜的卡，如果你需要的是那種卡，不要找我們。 雖然我們坦言自己是虛擬卡，但是我們並沒有寫我們明顯不能接的項目，比如Facebook，Discord，Line等等。 哪些能做，哪些不能做，我們還是很清楚的，畢竟耍小聰明賺不了大錢。',
      p8: 'Q：如何使用你們的平臺？',
      p9:'A：網頁前端的使用：',
      p10:'1.在接碼系統註冊帳號並登入，看看項目清單裏是否有你需要的項目',
      p11:'2.點擊“購買卡密通道”，10元卡密就是可以充值10元，其他以此類推',
      p12:'3.購買了之後，會得到一串卡密，在接碼系統頁面的“充值”裏，輸入卡密，對應的金額就顯示在你的餘額裏',
      p13:'4.在你需要的項目點擊“取號”，進入驗證碼獲取頁面。',
      p14:'5.點擊“獲取號碼”，會得到一個北美手機號，在App上輸入手機號碼進行注册，此時電話號碼會收到一個驗證碼，如果暫時沒出現，可能是App服務器原因有延遲，每5秒自動刷新一次，共試著獲取60次。',
      p15:'6.注册App成功之後，可以提前釋放號碼，點擊“停止接碼”即可。',
      p16:'前面是號碼，已自動省略區號+1，你需要的話自己加上。 中間是項目名稱Affirm，後邊是連結，在瀏覽器打開連結，自動讀取接碼的內容。 適合批量注册App的各種軟件。 連結默認是帶有我們官方網站的功能變數名稱，如果你需要轉手賣的話，聯系客服，可以不顯示我們的網站，有IP版，如 http://143.198.141.109/admin/auth/get/token?token=4efad311a49948b9a0ec4740aa5de1b4',
      p17:'如果需要通過API接碼，請看網站上的API說明。 API接碼也接受USDT的自動充值，直接充值到固定的地址後，餘額自動更新，方便你們平臺走量。',
      p18:'Q：號碼有效期多長？',
      p19: 'A：號碼有效期不定，時間有長有短，最長3個月。 如果你對時間要求比較高，比如一定要2個月以上的，且要多次接碼，請聯系客服。',
      p20:'Q：可以重複接碼嗎？',
      p21:'A：普通的用戶，在網頁前端無法重複接碼，因為系統裏的號碼太多，你每次獲取到的號碼不固定。 重複接碼的話，需要用URL來接碼，也就相當於固定了一批號碼給你。 如果需要重複接碼，聯系客服（50個起才接）。',
      p22:'Q：為什麼用URL取碼卻不來碼？',
      p23:'A：如果你是做倒買倒賣的，可能會出現這樣的問題。 兩個原因，一是因為你們的客戶的網路環境無法支持網站的CDN，我們用的是CloudFlare的CDN服務，國內有些網路環境不支持。 二是無法解析https，換成URL的IP版本就可以。',
      p24:'Q：你們是什麼埠？',
      p25:'A：提這個的往往不懂網絡基礎知識。 IP由四段組成，他們往往把IP的第一段叫做“埠”，這和真正的埠是兩碼事。 但是因為提這個的實在太多，我們也無奈，只好和他們說，我們是143埠。 如果你之前試過了其他人的143埠，然後認為接碼率很低，那不用我們的服務也不要緊，我們也不服務這類小白。',
      p26:'Q：如何添加我想要的項目？',
      p27:'A：常規的熱門項目，有很多項目是不支持虛擬卡接碼的，比如Discord，Line，Facebook，Tinder等等，如果你需要一些冷門項目的碼，聯系客服，需要發送接碼成功的簡訊範本給客服，讓客服添加項目。 前提是，必須有量。 為了你的一兩個碼添加項目，對我們來說不划算。',
      p28:'Q：為什麼Google，Microsoft，Apple接不了碼？',
      p29:'A：這三個都是大公司，每個公司下麵都有非常多的業務，每個業務其實是單獨的，所以一部分業務可以接碼不代表所有的都可以接碼。 比如Google Voice和Google雲服務，就是兩個不同業務。 這三家具體什麼業務可以接，可以諮詢客服。 也可以自己測試。',
      p30:'Q：可以退款嗎？',
      p31:'A：不可以。 帳號餘額會一直保留，你可以把帳號轉送給其他需要的人。',
    
    }
    
  },
  ...zhLocale
}
export default tc;
