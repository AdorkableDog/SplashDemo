package xiaobumall.printfuture.com.gaodemapwebview.entity;

import java.util.List;

/**
 * CategoryResult
 * Created by bakumon on 2016/12/8.
 */
public class CategoryResults {


    /**
     * data : [{"img":{"bottom":"http://center.paint-future.com/app/filemanager/file/file?key=39f8387ba657e499990286752b7cc692","top":"http://center.paint-future.com/app/filemanager/file/file?key=fd9653c3814d71a772c76eb380aa2fe3"},"key":"20cd74328c60b785","showtime":"0"},{"img":{"bottom":"http://center.paint-future.com/app/filemanager/file/file?key=0c15a0eb9b592b6ac1ff6ed4ec2494f3","top":"http://center.paint-future.com/app/filemanager/file/file?key=c7f179a3d4ccc5f0eecf58bd3c8e0ce2"},"key":"20cd74328c60b787","showtime":"0"},{"img":{"bottom":"http://center.paint-future.com/app/filemanager/file/file?key=1c3fc880e11df774ab0838eda206cc75","top":"http://center.paint-future.com/app/filemanager/file/file?key=28ebcb76cdbd01587d561f160a5e90f7"},"key":"20cd74328c60b897","showtime":20180208},{"img":{"bottom":"http://center.paint-future.com/app/filemanager/file/file?key=1c3fc880e11df774ab0838eda206cc75","top":"http://center.paint-future.com/app/filemanager/file/file?key=28ebcb76cdbd01587d561f160a5e90f7"},"key":"20cd74328c60b897","showtime":20180209}]
     * status : 0
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * img : {"bottom":"http://center.paint-future.com/app/filemanager/file/file?key=39f8387ba657e499990286752b7cc692","top":"http://center.paint-future.com/app/filemanager/file/file?key=fd9653c3814d71a772c76eb380aa2fe3"}
         * key : 20cd74328c60b785
         * showtime : 0
         */

        private ImgBean img;
        private String key;
        private String showtime;

        public ImgBean getImg() {
            return img;
        }

        public void setImg(ImgBean img) {
            this.img = img;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getShowtime() {
            return showtime;
        }

        public void setShowtime(String showtime) {
            this.showtime = showtime;
        }

        public static class ImgBean {
            /**
             * bottom : http://center.paint-future.com/app/filemanager/file/file?key=39f8387ba657e499990286752b7cc692
             * top : http://center.paint-future.com/app/filemanager/file/file?key=fd9653c3814d71a772c76eb380aa2fe3
             */

            private String bottom;
            private String top;

            public String getBottom() {
                return bottom;
            }

            public void setBottom(String bottom) {
                this.bottom = bottom;
            }

            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }
        }
    }
}
