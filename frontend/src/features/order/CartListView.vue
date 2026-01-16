<script setup>
import { ref, onMounted } from 'vue';
import { cartApi } from '../../api/cartApi';
import { orderApi } from '../../api/orderApi';
import { useRouter } from 'vue-router';

const router = useRouter();
const cartItems = ref([]);
const loading = ref(false);
const userId = 1; // Simulation
const storeId = 1; // Simulation

const deliveryInfo = ref({
  deliveryDate: new Date().toISOString().split('T')[0],
  address: '',
  receiverName: '',
  receiverPhone: '',
  memo: ''
});

const fetchCartItems = async () => {
  loading.value = true;
  try {
    const response = await cartApi.getCartItems(storeId);
    // API returns { data: [...] }
    cartItems.value = response.data || [];
  } catch (err) {
    console.error('Failed to fetch cart:', err);
  } finally {
    loading.value = false;
  }
};

const deleteItem = async (cartItemId) => {
  if (!confirm('정말 삭제하시겠습니까? 🥣')) return;
  try {
    await cartApi.deleteCartItem(storeId, cartItemId);
    await fetchCartItems();
  } catch (err) {
    alert('삭제에 실패했습니다.');
  }
};

const goBack = () => {
  router.push('/products');
};

const goToOrder = async () => {
  if (cartItems.value.length === 0) return;
  if (!confirm('🚀 이대로 주문을 진행하시겠습니까?')) return;

  loading.value = true;
  try {
    const orderData = {
      deliveryDate: deliveryInfo.value.deliveryDate,
      address: deliveryInfo.value.address,
      receiverName: deliveryInfo.value.receiverName,
      receiverPhone: deliveryInfo.value.receiverPhone,
      memo: deliveryInfo.value.memo
    };

    // Validation (optional but good)
    if (!orderData.address || !orderData.receiverName || !orderData.receiverPhone) {
      alert('📍 배송지 정보와 수령인 정보를 모두 입력해주세요!');
      loading.value = false;
      return;
    }
    
    await orderApi.createOrder(storeId, orderData);
    alert('✨ 주문이 성공적으로 접수되었습니다!');
    router.push('/inventory'); // Navigate back after order
  } catch (err) {
    console.error('Order creation failed:', err);
    alert('🔥 주문 처리에 실패했습니다. 다시 시도해주세요.');
  } finally {
    loading.value = false;
  }
};

onMounted(fetchCartItems);
</script>

<template>
  <div class="cart-page">
    <nav class="tteok-nav">
      <div class="nav-container">
        <div class="brand" @click="router.push('/inventory')">
          <span class="brand-icon">🌶️</span>
          <h1 class="brand-name">SPICY <span>ORDER</span></h1>
        </div>
        <button class="back-link" @click="goBack">◀ 상품 목록으로 돌아가기</button>
      </div>
    </nav>

    <header class="tteok-header">
      <div class="header-card premium-card">
        <h2>내 장바구니 🛒</h2>
        <p>선택하신 재료들이 신선하게 대기 중입니다.</p>
        <div class="header-decor">🥣</div>
      </div>
    </header>

    <main class="main-content">
      <div v-if="loading" class="loading-state">
        <div class="tteok-spinner">🥘</div>
        <p>장바구니를 확인 중입니다...</p>
      </div>

      <div v-else-if="cartItems.length === 0" class="empty-cart premium-card">
        <span class="empty-icon">🥣</span>
        <p>장바구니가 텅 비었네요.</p>
        <button class="btn-spicy" @click="goBack">재료 채우러 가기</button>
      </div>

      <div v-else class="cart-container">
        <div class="cart-list">
          <div v-for="item in cartItems" :key="item.cartItemId" class="cart-card premium-card">
            <div class="item-info">
              <span class="p-name">{{ item.product.productName }}</span>
              <span class="p-price">₩{{ item.product.price.toLocaleString() }}</span>
            </div>
            <div class="item-actions">
              <div class="qty-badge">수량: {{ item.quantity }}개</div>
              <button class="delete-btn" @click="deleteItem(item.cartItemId)">삭제</button>
            </div>
          </div>
        </div>

        <div class="cart-summary premium-card">
          <h3>배송 정보 입력</h3>
          <div class="delivery-form">
            <div class="input-group">
              <label>희망 배송일</label>
              <input type="date" v-model="deliveryInfo.deliveryDate" />
            </div>
            <div class="input-group">
              <label>수령인 이름</label>
              <input type="text" v-model="deliveryInfo.receiverName" placeholder="예: 홍길동" />
            </div>
            <div class="input-group">
              <label>연락처</label>
              <input type="text" v-model="deliveryInfo.receiverPhone" placeholder="010-0000-0000" />
            </div>
            <div class="input-group">
              <label>배송지 주소</label>
              <input type="text" v-model="deliveryInfo.address" placeholder="배송 받으실 주소를 입력하세요" />
            </div>
            <div class="input-group">
              <label>요청사항 (선택)</label>
              <textarea v-model="deliveryInfo.memo" placeholder="예: 문 앞에 놓아주세요"></textarea>
            </div>
          </div>

          <div class="summary-divider"></div>

          <h3>주문 예정 금액</h3>
          <div class="summary-row">
            <span>총 상품 금액</span>
            <strong>₩{{ cartItems.reduce((acc, cur) => acc + (cur.product.price * cur.quantity), 0).toLocaleString() }}</strong>
          </div>
          <div class="summary-row total">
            <span>최종 금액</span>
            <span class="total-price">₩{{ cartItems.reduce((acc, cur) => acc + (cur.product.price * cur.quantity), 0).toLocaleString() }}</span>
          </div>
          <button class="btn-spicy order-btn" @click="goToOrder">주문하기 ✨</button>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.cart-page {
  min-height: 100vh;
  background-color: #fffbeb;
}

.tteok-nav {
  background-color: #ffffff;
  border-bottom: 4px solid #fde68a;
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.brand { display: flex; align-items: center; gap: 0.75rem; cursor: pointer; }
.brand-icon { font-size: 1.75rem; }
.brand-name { font-size: 1.5rem; font-weight: 900; color: #451a03; margin: 0; }
.brand-name span { color: #e11d48; }

.back-link { background: none; border: none; font-weight: 800; color: #92400e; cursor: pointer; }

.tteok-header {
  max-width: 800px;
  margin: 2rem auto 0;
  padding: 0 2rem;
}

.header-card {
  padding: 2.5rem;
  background: white;
  border-radius: 32px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 20px 40px -10px rgba(0,0,0,0.05);
  border: 3px solid #fde68a;
}

.header-card h2 { font-size: 2.25rem; font-weight: 950; color: #451a03; margin: 0 0 0.5rem 0; }
.header-card p { color: #92400e; font-weight: 700; font-size: 1.1rem; }
.header-decor { position: absolute; right: 2rem; bottom: 0.5rem; font-size: 5rem; opacity: 0.1; }

.main-content {
  max-width: 800px;
  margin: 2rem auto;
  padding: 0 2rem;
}

.cart-container {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.cart-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.cart-card {
  background: white;
  padding: 1.5rem 2rem;
  border-radius: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05);
  border: 3px solid #fde68a;
  position: relative;
}

.item-info { display: flex; flex-direction: column; gap: 0.25rem; }
.p-name { font-size: 1.25rem; font-weight: 900; color: #451a03; }
.p-price { font-size: 1rem; font-weight: 700; color: #e11d48; }

.item-actions { display: flex; align-items: center; gap: 1.5rem; }
.qty-badge { background: #fffbeb; padding: 0.4rem 1rem; border-radius: 99px; font-weight: 800; color: #92400e; border: 1.5px solid #fde68a; }

.delete-btn {
  background: none;
  border: 1.5px solid #fecaca;
  color: #ef4444;
  padding: 0.4rem 1rem;
  border-radius: 12px;
  font-weight: 800;
  cursor: pointer;
  transition: all 0.2s;
}

.delete-btn:hover {
  background: #ef4444;
  color: white;
}

.cart-summary {
  background: white;
  padding: 2rem;
  border-radius: 32px;
  border: 3px solid #fde68a;
}

.cart-summary h3 { font-size: 1.5rem; font-weight: 900; color: #451a03; margin-bottom: 1.5rem; }
.summary-row { display: flex; justify-content: space-between; margin-bottom: 1rem; font-weight: 700; color: #92400e; }
.summary-row.total { border-top: 2px dashed #fde68a; padding-top: 1.5rem; margin-top: 0.5rem; }
.total-price { font-size: 1.75rem; font-weight: 950; color: #e11d48; }

.order-btn {
  width: 100%;
  height: 60px;
  border-radius: 18px;
  font-size: 1.25rem;
  font-weight: 900;
  margin-top: 1.5rem;
  background: #e11d48;
  color: white;
  border: none;
  box-shadow: 0 6px 0 #9f1239;
}

.delivery-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 2rem;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 0.4rem;
}

.input-group label {
  font-size: 0.9rem;
  font-weight: 800;
  color: #92400e;
}

.input-group input, .input-group textarea {
  padding: 0.8rem;
  border: 2px solid #fde68a;
  border-radius: 12px;
  font-weight: 600;
  color: #451a03;
  outline: none;
}

.input-group input:focus, .input-group textarea:focus {
  border-color: #e11d48;
}

.input-group textarea {
  height: 80px;
  resize: none;
}

.summary-divider {
  height: 2px;
  background: #fffbeb;
  margin-bottom: 2rem;
}

.empty-cart {
  text-align: center;
  padding: 5rem;
  background: white;
  border-radius: 32px;
  border: 3px solid #fde68a;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1.5rem;
}

.empty-icon { font-size: 5rem; opacity: 0.2; }
.empty-cart p { font-size: 1.5rem; font-weight: 900; color: #92400e; }

.btn-spicy {
  background: #e11d48;
  color: white;
  padding: 0.8rem 2rem;
  border-radius: 14px;
  font-weight: 800;
  border: none;
  cursor: pointer;
  box-shadow: 0 4px 0 #9f1239;
}

.tteok-spinner { font-size: 3rem; animation: spin 1s infinite linear; text-align: center; margin-bottom: 1rem; }
@keyframes spin { from { transform: rotate(0); } to { transform: rotate(360deg); } }

.loading-state { text-align: center; padding: 5rem; }
</style>
