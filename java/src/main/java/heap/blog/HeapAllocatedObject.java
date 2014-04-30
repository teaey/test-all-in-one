package heap.blog;/*
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class HeapAllocatedObject implements ObjectType {

    private HeapValue[] values;
    private int index;

    public HeapAllocatedObject(int element) {
        values = new HeapValue[element];
        for (int x = 0; x < element; x++) {
            values[x] = new HeapValue();
        }
    }

    @Override
    public int getInt() {
        return values[index].getId();
    }

    @Override
    public void setInt(int value) {
        values[index].setId(value);
    }

    @Override
    public long getLong() {
        return values[index].getLongValue();
    }

    @Override
    public void setLong(long value) {
        values[index].setLongValue(value);

    }

    @Override
    public byte getByte() {
        return values[index].getType();
    }

    @Override
    public void setByte(byte value) {
        values[index].setType(value);

    }

    @Override
    public void navigate(int index) {
        this.index = index;
    }


    private static class HeapValue {
        private int id;
        private long longValue;
        private byte type;

        public HeapValue() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getLongValue() {
            return longValue;
        }

        public void setLongValue(long longValue) {
            this.longValue = longValue;
        }

        public byte getType() {
            return type;
        }

        public void setType(byte type) {
            this.type = type;
        }

    }

}
